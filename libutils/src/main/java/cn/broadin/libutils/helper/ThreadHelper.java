package cn.broadin.libutils.helper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 在子线程中执行任务
 * Created by ChenHui on 2017/8/7.
 *
 * @param <T1> 不可以传null值！！！
 * @param <T2> 不可以传null值！！！
 */

public abstract class ThreadHelper<T1, T2> {

    /**
     * IO线程中执行
     */
    public void executeIO(T1 t1) {
        Observable.just(t1)
                .map(new Function<T1, T2>() {
                    @Override
                    public T2 apply(T1 t1) throws Exception {
                        return runOnBackground(t1);
                    }
                })
                .subscribeOn(Schedulers.io())//子线程中处理
                .observeOn(AndroidSchedulers.mainThread())//主线程中返回
                .subscribe(new Consumer<T2>() {
                    @Override
                    public void accept(T2 t2) throws Exception {
                        onEnd(t2);
                    }
                });
    }

    /**
     * 新线程中执行
     */
    public void execute(T1 t1) {
        Observable.just(t1)
                .map(new Function<T1, T2>() {
                    @Override
                    public T2 apply(T1 t1) throws Exception {
                        return runOnBackground(t1);
                    }
                })
                .subscribeOn(Schedulers.computation())//子线程中处理
                .observeOn(AndroidSchedulers.mainThread())//主线程中返回
                .subscribe(new Consumer<T2>() {
                    @Override
                    public void accept(T2 t2) throws Exception {
                        onEnd(t2);
                    }
                });

    }

    /**
     * 新线程中执行
     */
    public void executeNewThread(T1 t1) {
        Observable.just(t1)
                .map(new Function<T1, T2>() {
                    @Override
                    public T2 apply(T1 t1) throws Exception {
                        return runOnBackground(t1);
                    }
                })
                .subscribeOn(Schedulers.newThread())//子线程中处理
                .observeOn(AndroidSchedulers.mainThread())//主线程中返回
                .subscribe(new Consumer<T2>() {
                    @Override
                    public void accept(T2 t2) throws Exception {
                        onEnd(t2);
                    }
                });

    }

    protected abstract T2 runOnBackground(T1 p1);


    protected abstract void onEnd(T2 p1);

}
