package fr.jpsave.android.mymeteo.model;

public class CityWidget {
    private int widgetId;
    private City city;

    public CityWidget(int widgetId, City city) {
        this.widgetId = widgetId;
        this.city = city;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
