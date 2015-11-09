package com.bionic.edu;

import com.bionic.edu.matcher.ModelMatcher;
import com.bionic.edu.model.BaseEntity;
import com.bionic.edu.model.Request;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Created by VPortianko on 09.11.2015.
 */
public class RequestTestData {

    public static final ModelMatcher<Request, String> REQ_MATCHER = new ModelMatcher<>(Request::toString);

    public static final int REQ1_ID = BaseEntity.START_SEQ + 3;
    public static final int REQ2_ID = BaseEntity.START_SEQ + 4;
    public static final int REQ3_ID = BaseEntity.START_SEQ + 5;
    public static final int REQ4_ID = BaseEntity.START_SEQ + 6;

    public static final Request REQ1 = new Request(REQ1_ID, null,
            "Купил вчера ботинки в магазине под домом. Какой цвет брюк под них подойдет?",
            LocalDateTime.of(2015, Month.NOVEMBER,1,14,0),
            "http://proboty.ru/wp-content/gallery/top-10-zheltyye-botinki/zheltyye-botinki-top-3-inario.jpg");
    public static final Request REQ2 = new Request(REQ2_ID, null,
            "Моя новая юбка. С чем ее можно одеть?",
            LocalDateTime.of(2015, Month.NOVEMBER,1,18,0),
            "http://kompkroy.ru/wp-content/uploads/2012/04/yubka-polusolnce-vykroika-model.jpg");
    public static final Request REQ3 = new Request(REQ3_ID, null,
            "Нижнее белье. С чем сочетается?",
            LocalDateTime.of(2015, Month.NOVEMBER,2,15,0),
            "http://www.of-md.com/wp-content/uploads/2015/09/219.jpg");
    public static final Request REQ4 = new Request(REQ4_ID, null,
            "Туфельки с новой коллекции. Что скажете?",
            LocalDateTime.of(2015, Month.NOVEMBER,3,10,30),
            "http://modnaya.org/uploads/posts/2012-04/1335616617_tufli-na-vysokom-kabluke.jpg");
}
