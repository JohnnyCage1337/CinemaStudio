package com.example.io_app.DTO;

public class FindingFilmRequestDTO {

    private int id;
    private String title;

    public FindingFilmRequestDTO(String title){
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
