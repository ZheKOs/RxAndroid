package com.test.rxandroid;


import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainExample {

    Observable<List<String>> queryURLs(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(
                new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        subscriber.onNext(webParsing.getURLs(url));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    Observable<String> queryTitle(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(webParsing.getTitle(url));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
//...

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //EXAMPLES
    public void example0(final TextView textView, String url) {
        queryURLs(url)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> urls) {
                        for (String url: urls) {
                            String string = (String) textView.getText();
                            textView.setText(string + url + "\n\n");
                        }
                    }
                });
    }

    public void example1(final TextView textView, String url) {
        queryURLs(url)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> urls) {
                        Observable.from(urls)
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String url) {
                                        String string = (String) textView.getText();
                                        textView.setText(string + url + "\n\n");
                                    }
                                });
                    }
                });
    }

    public void example2(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String url) {
                        String string = (String) textView.getText();
                        textView.setText(string + url + "\n\n");
                    }
                });
    }

    public void example3(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example4(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title != null)
                .take(7)
                .map(title1 -> textView.getText() + title1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example5(final TextView textView, String url){

        List<String> list = new LinkedList<>();

        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title!=null)
                .map(this::print)
                .subscribe(list::add);

        for (String str:
             list) {
            System.out.println("from loop " + str);
        }
    }
    private String print(String str){
        System.out.println("FROM PRINT " + str);
        return str;
    }

}
