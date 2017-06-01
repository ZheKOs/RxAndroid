package com.test.rxandroid;


import android.icu.util.RangeValueIterator;
import android.os.Build;
import android.provider.DocumentsContract;
import android.widget.TextView;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


class WebParsing {
    public List<String> getURLs(String url) {

        Document doc;
        List<String> stringList = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
            Elements select = doc.select("a");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stringList.addAll(
                        select.stream()
                                .map(element -> element.attr("href"))
                                .collect(Collectors.toList())
                );
            }else{
                for (Element element : select) {
                    stringList.add(element.attr("href"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stringList;
    }

    public String getTitle(String url) {
        String title;
        try {
            Document doc = Jsoup.connect(url).get();
            title = doc.title();
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
            return null;
        } catch (HttpStatusException hse) {
            hse.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return null;
        }
        return title;
    }
}