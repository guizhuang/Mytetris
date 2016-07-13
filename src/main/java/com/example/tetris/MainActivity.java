package com.example.tetris;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    public ImageButton left, right, rotate,  speedUp,start;   //图片按钮


    public TextView score, maxScore, level, speed;       //标签

    public int scoreValue, maxScoreValue, levelValue, speedValue;     //标签值

    public String scoreString = "当前分数：", maxScoreString = "最高分数：", levelString = "等级：", speedString = "速度：";

    public TetrisView view;

    public ShowNextBlockView nextBlockView;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取各组件和标签值
        view = (TetrisView) findViewById(R.id.tetrisView);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);
        rotate = (ImageButton) findViewById(R.id.rotate);
        start = (ImageButton) findViewById(R.id.start);
        speedUp = (ImageButton) findViewById(R.id.speedUp);
        nextBlockView = (ShowNextBlockView) findViewById(R.id.nextBlockView);
        nextBlockView.invalidate();//view的更新
        score = (TextView) findViewById(R.id.score);
        maxScore = (TextView) findViewById(R.id.maxScore);
        level = (TextView) findViewById(R.id.level);
        speed = (TextView) findViewById(R.id.speed);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        scoreValue = maxScoreValue = 0;
        levelValue = speedValue = 1;

        score.setText(scoreString+scoreValue);
        level.setText(""+levelValue);
        speed.setText(""+speedValue);
        maxScore.setText(maxScoreString+maxScoreValue);


        //设置各按钮的监听器
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove)                   //判断能否左移
                    view.getFallingBlock().move(-1);//执行向左移动
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();          //刷新view
                    }
                });
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove)
                    view.getFallingBlock().move(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove == false)
                    return;
                TetrisBlock copyOfFallingBlock = view.getFallingBlock().clone();
                copyOfFallingBlock.rotate();                         //克隆当前俄罗斯方块，进行变换
                if (copyOfFallingBlock.canRotate()) {
                    TetrisBlock fallinBlock = view.getFallingBlock();//获取变换后的方块
                    fallinBlock.rotate();                            //执行变换
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });        //更新view
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start.setBackground();//设置运行和暂停背景

                //android.os.Handler,这个可以实现各处线程间的消息传递。
                //通过message，多个进程交流
                final Handler handler = new Handler(){
                    public void handleMessage(Message msg){
                        switch (msg.what){
                            case 1:
                                progressBar.incrementProgressBy(-5);
                                if (progressBar.getProgress()<3){
                                    progressBar.setProgress(100);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                };
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                };
                Timer  timer = new Timer(true);
                timer.schedule(task,1000,1000);
                view.init();

            }
        });
        speedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.canMove) {
                    view.getFallingBlock().setY(view.getFallingBlock().getY() + BlockUnit.UNITSIZE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.invalidate();
                        }
                    });
                }
            }
        });
        view.setFather(MainActivity.this);
        view.invalidate();

    }

}
