package com.scala.exp.android.sdk.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.scala.exp.android.sdk.AppSingleton;
import com.scala.exp.android.sdk.Utils;
import com.scala.exp.android.sdk.model.Content;

import java.lang.reflect.Type;

/**
 * Created by Cesar Oyarzun on 10/30/15.
 */
public class ContentJsonAdapter implements JsonDeserializer<Content>,IExpDeserializer {

    public static final String SUBTYPE = "subtype";
    public static final String CHILDREN = "children";

    @Override
    public Content deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
            context) throws JsonParseException {
        return expDeserialzier(json);
    }

    @Override
    public Content expDeserialzier(JsonElement json) {
        return deserialize(json);
    }

    public static Content deserialize(JsonElement json){
        LinkedTreeMap treeMap = AppSingleton.getInstance().getGson().fromJson(json, LinkedTreeMap.class);
        String subtype = (String) treeMap.get(SUBTYPE);
        Content contentNode = new Content(Utils.getContentTypeEnum(subtype));
        contentNode.setProperties(treeMap);
        return contentNode;
    }
}