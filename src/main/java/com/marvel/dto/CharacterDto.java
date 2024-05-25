package com.marvel.dto;

import java.util.ArrayList;
import java.util.List;

public class CharacterDto {

    private Integer id;
    private String name;
    private String description;
    private ImageDto image;
    private List<ComicDto> listComics;
    private List<SerieDTo> listSeries;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ComicDto> getListComics() {
        if (listComics == null) listComics = new ArrayList<>();
        return listComics;
    }

    public List<SerieDTo> getListSeries() {
        if (listSeries == null) listSeries = new ArrayList<>();
        return listSeries;
    }

    public ImageDto getImage() {
        return image;
    }

    public void setImage(ImageDto image) {
        this.image = image;
    }

    public CharacterDto() {
    }

    @Override
    public String toString() {
        return "CharacterDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", listComics=" + listComics +
                ", listSeries=" + listSeries +
                '}';
    }
}
