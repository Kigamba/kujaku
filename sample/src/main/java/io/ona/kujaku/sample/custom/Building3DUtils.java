package io.ona.kujaku.sample.custom;

import android.support.annotation.NonNull;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.FillExtrusionLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;

public class Building3DUtils {

    public static void addBuildingExtrusionLayer(@NonNull MapboxMap mapboxMap) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                // Add the extrusion
                addBuildingExtrusionLayer(style);
            }
        });

    }

    public static void addBuildingExtrusionLayer(@NonNull Style style) {
        //FillExtrusion Layer
        FillExtrusionLayer fillExtrusionLayer = new FillExtrusionLayer("3d-buildings", "composite");
        fillExtrusionLayer.setSourceLayer("building");
        fillExtrusionLayer.setProperties(PropertyFactory.fillExtrusionColor("#aaa")
                , PropertyFactory.fillExtrusionHeight(Expression.get("height"))
                , PropertyFactory.fillExtrusionBase(Expression.get("min_height"))
                , PropertyFactory.fillExtrusionOpacity(.6f));

        style.addLayer(fillExtrusionLayer);
    }
}
