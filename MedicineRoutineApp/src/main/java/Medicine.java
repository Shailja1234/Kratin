import java.util.List;

public class Medicine {
    int id;
    String name;
    List<String> timeOfConsumption;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTimeOfConsumption() {
        return timeOfConsumption;
    }

    public String getStringifyDosageTimes() {
        String x = "";
        for(int i=0;i<timeOfConsumption.size();i++) {
            if(i<timeOfConsumption.size()-1)
                x += timeOfConsumption.get(i).trim() + "\\,";
            else
                x += timeOfConsumption.get(i).trim();
        }
        return x;
    }

    public void setTimeOfConsumption(List<String> timeOfConsumption) {
        this.timeOfConsumption = timeOfConsumption;
    }
}
