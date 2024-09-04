package com.potatomeme.custom_ui.sample.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View

//맞춤 뷰 구성요소 만들기
//refs : https://developer.android.com/develop/ui/views/layout/custom-views/custom-components?hl=ko

class SampleView @JvmOverloads constructor(// @JvmOverloads 의 경우 xml에서 사용할때 받는 값들
    context: Context,//xml에서 사용시 받음
    attrs: AttributeSet? = null,//이경우 xml 에서 받는 속성값들을 의미함 해당값들을 xml로 지정하여 해당값들을 AttributeSet의 형태로 받을수있음
    defStyle: Int = 0,//
) : View(context, attrs, defStyle) {
    //View말고도 Button, TextView, EditText, ListView, CheckBox, RadioButton, Gallery, Spinner 같은 위젯을 상속 받을 수있으면
    //AutoCompleteTextView, ImageSwitcher 및 TextSwitcher 같은 특수한 경우로도 사용할수있음


    //생성 -----------------------------------------------------------------------------------------------
    init {
        //레이아웃 파일에서 뷰가 확장될 때 호출되는 양식
        //레이아웃 파일에 정의된 속성을 파싱하고 적용 ex) attrs
    }

    //뷰와 뷰의 모든 하위 요소가 XML에서 확장되었을 때 호출됩니다.
    override fun onFinishInflate() {
        super.onFinishInflate()
    }


    //레이아웃 -----------------------------------------------------------------------------------------------
    //이 뷰 및 모든 하위의 크기 요구사항을 결정하기 위해 호출됩니다.
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    //뷰가 모든 하위 요소에 크기와 위치를 할당해야 할 때 호출됩니다.
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    //이 뷰의 크기가 변경될 때 호출됩니다.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }


    //그리기 -----------------------------------------------------------------------------------------------
    //뷰가 콘텐츠를 렌더링해야 할 때 호출됩니다.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }


    //이벤트 처리 -----------------------------------------------------------------------------------------------
    //키 다운 이벤트가 발생할 때 호출됩니다.
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    //주요 업 이벤트가 발생할 때 호출됩니다.
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
    }

    //트랙볼 모션 이벤트가 발생할 때 호출됩니다.
    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        return super.onTrackballEvent(event)
    }

    //터치스크린 모션 이벤트가 발생할 때 호출됩니다.
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }


    //포커스 -----------------------------------------------------------------------------------------------
    //뷰가 포커스를 얻거나 잃을 때 호출됩니다.
    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
    }

    //뷰가 포함된 창이 포커스를 얻거나 잃을 때 호출됩니다.
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
    }


    //연결 -----------------------------------------------------------------------------------------------
    //뷰가 창에 연결될 때 호출됩니다.
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    //뷰가 창에서 분리될 때 호출됩니다.
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    //뷰가 포함된 창의 가시성이 변경될 때 호출됩니다.
    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
    }
}
