    package com.example.io_app.DOMAIN.Film;

    public class Film {

        private int id;
        private String title;
        private String genre;
        private int duration;

        public Film(String title, String genre, int duration){
            if (title == null || title.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            if (genre == null || genre.isEmpty()) {
                throw new IllegalArgumentException("Genre cannot be empty");
            }
            if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be positive");
            }
            this.title = title;
            this.duration = duration;
            this.genre = genre;
        }

        public Film(int id, String title, String genre, int duration){
            if (title == null || title.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            if (genre == null || genre.isEmpty()) {
                throw new IllegalArgumentException("Genre cannot be empty");
            }
            if (duration <= 0) {
                throw new IllegalArgumentException("Duration must be positive");
            }
            this.id = id;
            this.title = title;
            this.duration = duration;
            this.genre = genre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        @Override
        public String toString() {
            return this.title + ", " + this.genre + ", " + this.duration;
        }
    }
