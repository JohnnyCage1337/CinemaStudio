package com.example.io_app.DTO.Film;

public class FindFilmRequestDTO {

    private int id;
    private String title;

    public FindFilmRequestDTO(String title){
        this.title=title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }


}
