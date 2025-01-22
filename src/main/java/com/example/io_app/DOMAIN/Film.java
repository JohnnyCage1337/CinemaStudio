    package com.example.io_app.DOMAIN;

    public class Film {

        private String name;
        private int duration;
        private String description;
        private String genre;
        private String rate;
        private String director;
        private String language;

        public Film(String name, int duration, String description, String genre, String rate, String director, String language){
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            if (genre == null || genre.isEmpty()) {
                throw new IllegalArgumentException("Genre cannot be empty");
            }
            if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be positive");
            }
            this.name = name;
            this.duration = duration;
            this.description = description;
            this.genre = genre;
            this.rate = rate;
            this.director = director;
            this.language = language;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }
