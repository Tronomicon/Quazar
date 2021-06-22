package com.example.QuazarApp;

import java.util.List;

class RoverPhotoRover {
    private int id;
    private String name;
    private String landing_date;
    private String launch_date;
    private String status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanding_date() {
        return landing_date;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getStatus() {
        return status;
    }
}
class RoverPhotoCamera {
    private int id;
    private String name;
    private int rover_id;
    private String full_name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRover_id() {
        return rover_id;
    }

    public String getFull_name() {
        return full_name;
    }
}
class RoverPhoto {
    private int id;
    private int sol;
    private RoverPhotoCamera camera;
    private String img_src;
    private String earth_date;
    private RoverPhotoRover rover;

    public int getId() {
        return id;
    }

    public int getSol() {
        return sol;
    }

    public RoverPhotoCamera getCamera() {
        return camera;
    }

    public String getImg_src() {
        return img_src;
    }

    public String getEarth_date() {
        return earth_date;
    }

    public RoverPhotoRover getRover() {
        return rover;
    }
}
public class RoverPhotos {
    private List<RoverPhoto> latest_photos;

    public List<RoverPhoto> getLatestPhotos() {
        return latest_photos;
    }
}