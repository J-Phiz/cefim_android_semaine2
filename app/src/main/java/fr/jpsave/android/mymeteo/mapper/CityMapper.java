package fr.jpsave.android.mymeteo.mapper;

import fr.jpsave.android.mymeteo.dto.CityDTO;
import fr.jpsave.android.mymeteo.model.City;

public class CityMapper {

    public static City fromDto(City entity, CityDTO dto) {
        entity.setmIdCity(dto.id);
        entity.setmLat(dto.coord.lat);
        entity.setmLon(dto.coord.lon);
        return entity;
    }

    public static City fromDto(CityDTO dto) {
        return fromDto(
                new City(
                        dto.name.replace("Arrondissement de ", ""),
                        dto.weather[0].description,
                        String.format("%2.1f°C", dto.main.temp),
                        Integer.parseInt(dto.weather[0].icon.substring(0,2))
                ),
                dto
        );
    }
}
