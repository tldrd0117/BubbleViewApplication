package com.sungjae.bubbleviewapplication;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by iseongjae on 2017. 8. 10..
 */

public class BubbleFrameLayout extends FrameLayout{
    float minSize;
    float maxSize;
    Random random;
    Disposable disposable;

    public BubbleFrameLayout(@NonNull Context context) {
        super(context);
        init();

    }

    public BubbleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public BubbleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public BubbleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init(){
        minSize = getResources().getDimension(R.dimen.min_circle);
        maxSize = getResources().getDimension(R.dimen.max_circle);
        random = new Random();
        startAnimation();
        Observable.timer(1, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                startAnimation();
            }
        }).subscribe();

        Observable.timer(2, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                startAnimation();
            }
        }).subscribe();

        Observable.timer(3, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                startAnimation();
            }
        }).subscribe();

        Observable.timer(4, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                startAnimation();
            }
        }).subscribe();

        Observable.timer(5, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                startAnimation();
            }
        }).subscribe();
    }

    private void startAnimation(){
        disposable = Observable.timer( 3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).concatMap(new Function<Long, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                final View view = generateCircleView();
                final int randomSize = (int) getRandomSize();
                Log.d("randomSize", ""+ randomSize);
                Log.d("minSize", ""+ minSize);

                addView( view, 0, new ViewGroup.LayoutParams(randomSize, randomSize));
                view.setTranslationY(getHeight());
                final float randomStartX = getRandomStart();
                view.setTranslationX(randomStartX);
                view.animate().translationY(0).setDuration(2500).start();
                view.animate().translationX(randomSize).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        view.animate().translationX(randomStartX + randomSize).setDuration(500).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                view.animate().translationX(randomStartX - randomSize).setDuration(500).setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        view.animate().translationX(randomStartX + randomSize).setDuration(500).setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animator) {
                                                view.animate().translationX(randomStartX-randomSize).setDuration(500).setListener(new Animator.AnimatorListener() {
                                                    @Override
                                                    public void onAnimationStart(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationCancel(Animator animator) {

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animator animator) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animator) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                return Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).concatMap(new Function<Long, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        removeView(view);
                        return Observable.empty();
                    }
                });
            }
        }).repeat().subscribe();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposable.dispose();
    }

    public float getRandomSize(){
        float size = Math.abs(random.nextInt()%(maxSize- minSize)) + minSize;
        return size;
    }

    public float getRandomStart(){
        float start = Math.abs(random.nextInt()%getWidth());
        return start;
    }

    private View generateCircleView(){
        View view = new View(getContext());
        view.setBackgroundResource(R.drawable.circle);
        return view;
    }

}
