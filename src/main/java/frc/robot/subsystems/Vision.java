package frc.robot.subsystems;

import javax.sound.sampled.DataLine;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision
{
    final StringSubscriber dblSub;
    
    public Vision(StringTopic strTopiStringTopic)
    {
        // dblSub = strTopiStringTopic.subscribe("");
        // dblSub = dblTopic.subscribe(0.0, PubSubOption.keepDuplicates(true), PubSubOption.pollStorage(10)); // hmm not sure about this
        dblSub = strTopiStringTopic.subscribeEx("string", "dt:0"); // This might be the better way to use it, passing all the properties as a string message
    }
    //rambling thoughts: dt:01;id:01;dx:456;dy:789;dz:789;yw:789;pt:456;rl:865
    //Might have to override this if we make this a subsystem
    public void periodic()
    {
        String data = dblSub.get();
        String[] properties = data.split(";");
        int detectedAprilTags = Integer.parseInt(properties[0].split(":")[1]);

        int offSet = 1;
        int numOfProperties = 7;
        for(int aprilTagIteration = 0; aprilTagIteration < detectedAprilTags; aprilTagIteration++)
        {
            int id = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 0].split(":")[1]);
            int distance_x = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 1].split(":")[1]); 
            int distance_y = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 2].split(":")[1]); 
            int distance_z = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 3].split(":")[1]); 
            int yaw = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 4].split(":")[1]); 
            int pitch = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 5].split(":")[1]); 
            int roll = Integer.parseInt(properties[offSet + aprilTagIteration * numOfProperties + 6].split(":")[1]);

            String output = "";
            output += "ID: " + id + "\n";
            output += "Distance X: " + distance_x + "\n";
            output += "Distance Y: " + distance_y + "\n";
            output += "Distance Z: " + distance_z + "\n";
            output += "Yaw: " + yaw + "\n";
            output += "Pitch: " + pitch + "\n";
            output += "Roll: " + roll + "\n";

            System.out.println(output);
            SmartDashboard.putString("TEST_APRIL_TAG", output);
        }
    }
}
