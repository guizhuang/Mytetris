package com.example.tetris;

/**
 * Created by win on 2016/7/8.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Terence on 2016/2/5.
 */

public class ShowNextBlockView extends View {
    /*
     * 用以显示下一个俄罗斯方块
     */

    public TetrisBlock nextBlock = null; //保存下一个俄罗斯方块

    public TetrisBlock createNextBlock(){
        /*
         * 创建下一个俄罗斯方块
         */
        nextBlock = new TetrisBlock(TetrisView.beginX,TetrisView.beginPoint);
        return  nextBlock;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //画布画笔初始化
//        canvas.drawColor(Color.WHITE);
//        canvas.drawBitmap();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(0);

        RectF rel;
        float size = BlockUnit.UNITSIZE;

        //方块颜色
        int color[] = {0,Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN,Color.GRAY};
        if(nextBlock!=null) {
            //画出下一个俄罗斯方块

//            paint.setColor(color[nextBlock.getColor()]);
            paint.setColor(0xFFFFFAFA);
            for (BlockUnit u : nextBlock.getUnits()) {

                //设置填充风格
                paint.setStyle(Paint.Style.FILL_AND_STROKE);

//                paint.setColor(color[nextBlock.getColor()]);
                paint.setColor(0xFFFFFAFA);


                //获取每个方块的横纵坐标
                float tx = (float)(u.getX() - TetrisView.beginX + BlockUnit.UNITSIZE * 2.5);
//                float ty = u.getY() + BlockUnit.UNITSIZE  ;
                float ty = u.getY() + BlockUnit.UNITSIZE*2  ;

                //创建圆角矩形
                rel = new RectF(tx, ty, tx + size, ty + size);
                canvas.drawRoundRect(rel, 8, 8, paint);

                //画出边界
//                paint.setColor(Color.LTGRAY);
                paint.setColor(0xFFFFFAFA);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                canvas.drawRoundRect(rel, 8, 8, paint);
            }
        }
    }
    public ShowNextBlockView(Context context) {
        /*
         * 构造函数
         */
        super(context);
    }
    public ShowNextBlockView(Context context, AttributeSet attrs) {
        /*
         * 构造函数
         */
        super(context, attrs);
    }
}