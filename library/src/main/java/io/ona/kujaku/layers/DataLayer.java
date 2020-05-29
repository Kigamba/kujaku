package io.ona.kujaku.layers;

import android.graphics.Color;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.NonNull;

/**
 * Created by Kigamba (nek.eam@gmail.com) on 28-May-2020
 */
public class DataLayer extends KujakuLayer {

    private String layerId = UUID.randomUUID().toString();
    private String sourceId = UUID.randomUUID().toString();

    private GeoJsonSource dataSource;
    private CircleLayer dataLayer;
    private ArrayList<Feature> features = new ArrayList<>();

    public static final String ID = "id";

    public DataLayer() {
    }

    @Override
    public void addLayerToMap(@NonNull MapboxMap mapboxMap) {
        createLayers(mapboxMap);
        mapboxMap.getStyle()
                .addSource(dataSource);
        mapboxMap.getStyle().addLayer(dataLayer);
    }

    protected void createLayers(@NonNull MapboxMap mapboxMap) {

        if (dataSource == null) {
            dataSource = new GeoJsonSource(sourceId);
        }
        dataLayer = new CircleLayer(layerId, sourceId);

        // Create the source
        if (mapboxMap.getStyle().getSource(sourceId) != null) {
            sourceId = UUID.randomUUID().toString();
        }

        // Create the layer
        if (mapboxMap.getStyle().getLayer(layerId) != null) {
            layerId = UUID.randomUUID().toString();
        }

        createSource();
        createLayer();
    }

    private void createLayer() {
        dataLayer.withProperties(
                PropertyFactory.circleRadius(10f),
                PropertyFactory.circleColor(Color.RED),
                PropertyFactory.circleOpacity(.7f));
    }

    private void createSource() {
        dataSource.setGeoJson(FeatureCollection.fromFeatures(features));
    }


    @Override
    public void enableLayerOnMap(@NonNull MapboxMap mapboxMap) {

    }

    @Override
    public void disableLayerOnMap(@NonNull MapboxMap mapboxMap) {

    }

    @NonNull
    @Override
    public String[] getLayerIds() {
        return new String[0];
    }

    @Override
    public boolean removeLayerOnMap(@NonNull MapboxMap mapboxMap) {
        return false;
    }

    @Override
    public void updateFeatures(@NonNull FeatureCollection featureCollection) {
    }

    public void updateFeature(@NonNull Feature feature) {
        Feature foundFeature;

        if (feature.properties() == null || !feature.properties().has(ID)) {
            feature.addStringProperty(ID, UUID.randomUUID().toString());
        }

        for (Feature collectionFeature: features) {
            String featureId = collectionFeature.getStringProperty(ID);
            if (collectionFeature == feature || feature.getStringProperty(ID).equals(featureId)) {
                features.remove(feature);
                features.add(feature);
            }
        }
    }

    public void addFeature(@NonNull Feature feature) {
        if (feature.properties() == null || !feature.properties().has(ID)) {
            feature.addStringProperty(ID, UUID.randomUUID().toString());
        }

        for (Feature availableFeature: features) {
            String featureId = availableFeature.getStringProperty(ID);
            if (feature.getStringProperty(ID).equals(featureId)) {
                return;
            }
        }

        features.add(feature);
        dataSource.setGeoJson(FeatureCollection.fromFeatures(features));
    }

    @Override
    public FeatureCollection getFeatureCollection() {
        return null;
    }
}
