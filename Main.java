/**
 * @author Baxter J B Berdinner
 * @version 16/05/2022
 */

//Import nessesary java extensions
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main
{
    //Declaration of default map dimensions. Theese can be changed so they are not constants.
    int width = 32;
    int height = 32;
    int depth = 128;

    int repeats = 4;

    final int WAIT1SEC = 1;
    final int WAIT2SEC = 2;
    final int WAIT3SEC = 3;
    final int WAIT10MILLISEC = 10;
    final int WAIT20MILLISEC = 20;

    //Create 2D array that holds map
    public String map[][];

    //Variables for types of pixels on map.
    public String Land = ("██");
    public String Hills = ("▓▓");
    public String Coast = ("▒▒");
    public String Sea = ("░░");

    //Creates new string "Prompt" which will be used to store keyboard input.
    String Prompt = new String();

    //Creates new string "Style" which will be used to storethe map style.
    String Style = new String();

    //Creates new keyboard scanner "input" which reads keyboard.
    Scanner input = new Scanner(System.in);

    public Main() //This is the functiuon that is ran first by the program.
    {
        StartText(); //Run StartText function.
    }

    public void StartText() //Displays the text the user will se when program first runs. Gives basic instructions on avalable command inputs.
    {
        //Clear screen and print out starting text.
        System.out.print('\u000c');

        System.out.println("█▀▄▀█ ▄▀█ █▀█   █▀▀ █▀▀ █▄░█ █▀▀ █▀█ ▄▀█ ▀█▀ █▀█ █▀█   █▀█ █▀█ █▀█ █▀█");
        System.out.println("█░▀░█ █▀█ █▀▀   █▄█ ██▄ █░▀█ ██▄ █▀▄ █▀█ ░█░ █▄█ █▀▄   ▀▀█ █▄█ █▄█ █▄█\n");     

        System.out.println("Type \"START\" to begin.");
        System.out.println("Type \"MAP\" to customise map dimensions.");
        System.out.println("Type \"INFO\" for info about this project.");
        System.out.println("Type \"QUIT\" to quit. \n");

        System.out.println("\nType \"3D\" for experimental 3D feature.\n");

        StartSettings(); //Runs function StartSettings.
    }

    public void AltStartText() //Same as start text, but does not clear screen or display title. 
    {
        System.out.println("Type \"START\" to begin.");
        System.out.println("Type \"MAP\" to customise map dimensions.");
        System.out.println("Type \"INFO\" for info about this project.");
        System.out.println("Type \"QUIT\" to quit. \n");

        System.out.println("\nType \"3D\" for experimental 3D feature.\n");

        StartSettings(); //Runs function StartSettings.
    }

    public void StartSettings() //The functionality of the command input system. 
    {
        Prompt = input.nextLine(); //Set "Prompt" to  be keyboard input.

        if(Prompt.equalsIgnoreCase("START")) //Checks to see if "Prompt" is equal to "START." Not case-sensitive.
        {
            GenerateMap(); //runs function GenerateMap.
        } else 
        if(Prompt.equalsIgnoreCase("3D"))
        {
            System.out.print('\u000c');
            GenerateMap3D();
        } else 
        if(Prompt.equalsIgnoreCase("MAP"))
        {
            CustomiseMap();
            AltStartText();
        } else 
        if(Prompt.equalsIgnoreCase("INFO"))
        {
            System.out.print('\u000c');

            System.out.println("█▀▄▀█ ▄▀█ █▀█   █▀▀ █▀▀ █▄░█ █▀▀ █▀█ ▄▀█ ▀█▀ █▀█ █▀█   █▀█ █▀█ █▀█ █▀█");
            System.out.println("█░▀░█ █▀█ █▀▀   █▄█ ██▄ █░▀█ ██▄ █▀▄ █▀█ ░█░ █▄█ █▀▄   ▀▀█ █▄█ █▄█ █▄█\n");     

            System.out.println("Made by BERDIBA");
            System.out.println("EXPORT code inspired by Bill Viggers.\n");
            System.out.println("All other code and images original.\n");

            System.out.println("Map Width: " + width);
            System.out.println("Map Height: " + height);
            System.out.println("Map Depth: " + depth + "\n");

            try {TimeUnit.SECONDS.sleep(WAIT2SEC);} catch(InterruptedException e){}
            AltStartText();
        } else
        if(Prompt.equalsIgnoreCase("QUIT"))
        {
            System.out.print('\u000c');

            System.exit(0); //Terminates program.
        } else 
        {
            System.out.println("Unknown command. Please try again. \n");
            StartSettings();
        }
    }

    public void CustomiseMap() //Includes all settings for map customisation.
    {
        System.out.print('\u000c'); //Clear screen.

        System.out.println("Enter an integer from 8-128. \n");

        //Set map width and height.
        System.out.println("Set map WIDTH: \n");
        String Width = input.nextLine().replaceAll("\\D", ""); //Sets new String "Width" to be keyboard input.
        if(!Width.equals("")) //If statement checks if Width string is empty
        {
            if(Integer.parseInt(Width) >= 8 && Integer.parseInt(Width) <= 128)
            {
                width = Integer.parseInt(Width); //Parses String "Width" into int "width."
                System.out.println("WIDTH set to "+width+"\n");
            } else
                System.out.println("Failed to set WIDTH: out of bounds numerical input. \n");
        } else
            System.out.println("Failed to set WIDTH: non-numerical input. \n");

        System.out.println("Set map HEIGHT: \n");
        String Height = input.nextLine().replaceAll("\\D", "");
        if(!Height.equals("")) //If statement checks if Height string is empty
        {
            if(Integer.parseInt(Height) >= 8 && Integer.parseInt(Height) <= 128)
            {
                height = Integer.parseInt(Height); //Parses String "Height" into int "height."
                System.out.println("HEIGHT set to "+height+"\n");
            } else
            {
                System.out.println("Failed to set HEIGHT: out of bounds numerical input. \n");
                try {TimeUnit.SECONDS.sleep(WAIT1SEC);} catch(InterruptedException e){}
            }
        } else
        {
            System.out.println("Failed to set HIGHT: non-numerical input. \n");
            try {TimeUnit.SECONDS.sleep(WAIT1SEC);} catch(InterruptedException e){}
        }
    }

    //The main generation fucntion of the program.
    public void GenerateMap()
    {
        //Variables for generating hills and land.
        double landChance = 0;
        double hillChance = .1;
        
        //Variables for chance (%) of generating land vs sea.
        double veryHigh = .98;
        double high = .9;
        double medHigh = .6;
        double medLow = .4;
        double low = .1;
        double veryLow = .02;

        map = new String[height][width]; //2D string array in which the map will be stored.

        //Initial map generation
        for (int y=0;y<height;y++) //While variable y is less than height variable, run code below then increase y by 1.
        {
            for (int x=0;x<width;x++) //While variable x is less than height variable, run code below then increase x by 1.
            {
                if(y > 0 && y < height-1 && x > 0 && x < width-1) //Checks to see if specified pixel is within bounds of map.
                {
                    if(Math.random() <= landChance) //Checks to see if a random number between 0 and 1 is greater or equal to variable landChance.
                    {
                        map[y][x] = Land; //Sets specified space on 2D map array to variable "Land".

                        if (y > 1) //if y > 1, check to see if the square above is Land. This cannot be done if y = 1, as the above square will always be Border.
                        {
                            if (map[y-1][x].equals(Land))
                                landChance = veryHigh;
                            else
                                landChance = medHigh;
                        } else
                            landChance = high;
                    } else 
                    {
                        map[y][x] = Sea; //If selected pixel is not land, set it to sea.

                        if (y > 1)
                        {
                            if (map[y-1][x].equals(Sea))
                                landChance = veryLow;
                            else
                                landChance = medLow;
                        } else
                            landChance = low; 
                        //Sea is more likely to generate if there are adjacent sea pixels. The same is true for land.
                        //This effect gives large blobs of land and sea, making the map look more realistic.
                    }
                } else
                    map[y][x] = Coast; 
                //This will later be changed in MapDisplay class to be the border, however is represented in text form using the same symbol as Coast.
            }
        }
        //Generation of coastline. Checks if adjacent pixels are sea and if selected pixel is not sea. If true, sets current pixel to coast.
        for (int y=0;y<height;y++)
        {
            for (int x=0;x<width;x++)
            {
                if(y > 0 && y < height-1 && x > 0 && x < width-1)
                {
                    if(map[y-1][x].equals(Sea) || map[y][x-1].equals(Sea) || map[y+1][x].equals(Sea) || map[y][x+1].equals(Sea))
                    {
                        if(!map[y][x].equals(Sea))
                            map[y][x] = Coast;
                    } 
                } 
            }
        }
        //Generation of hills. Sets random pixels that are land to hills.
        for (int y=0;y<height;y++)
        {
            for (int x=0;x<width;x++)
            {
                if(y > 0 && y < height-1 && x > 0 && x < width-1)
                {
                    if(map[y][x].equals(Land))
                    {
                        if(Math.random() <= hillChance)
                            map[y][x] = Hills;
                    } 
                } 
            }
        }

        int totalLand = 0;
        
        //Counts how much land is in the map.
        for (int y=0;y<height;y++)
        {
            for (int x=0;x<width;x++)
            {
                if(map[y][x].equals(Land))
                totalLand++;
            }
        }
        
        //If the total ammount of land is less than one eigth of the total map, regenrate the map.
        //This is done to ensure the map has an even balance of water and land.
        if(totalLand <= (width*height/8))
        GenerateMap();
        
        PrintMap(); //After map generation, print out map and run nessesary code for UI.
        MapText(); 
        MapSettings();
    }

    public void PrintMap()
    {
        System.out.print('\u000c'); //Clears screen
        System.out.println("Map preview:");

        //Map printing.
        for (int y=0;y<height;y++) //Moves through each row of the map.
        {
            for (int x=0;x<width;x++) //Moves through each collumn of the map.
            {
                System.out.print(map[y][x]); //Print specified pixel on screen.
            }
            System.out.println();
            try {TimeUnit.MILLISECONDS.sleep(WAIT10MILLISEC);} catch(InterruptedException e){} //Waits 10 milliseconds before generating the next slice.
        }
    }

    public void MapText() 
    {
        System.out.println("\nType \"REGEN\" to generate new map.");
        System.out.println("Type \"EXPORT\" to convert map to image.");
        System.out.println("Type \"MENU\" to return to main menu.");
        System.out.println("Type \"QUIT\" to quit.");
    }

    public void MapSettings() 
    {
        //If statements for character input.
        Prompt = input.nextLine();
        if(Prompt.equalsIgnoreCase("REGEN"))
        {
            System.out.print('\u000c'); //Clears screen.
            GenerateMap(); //Regenrates the map.
        } else
        if(Prompt.equalsIgnoreCase("EXPORT"))
        {
            ExportMap();
        } else
        if(Prompt.equalsIgnoreCase("QUIT"))
        {
            //Terminates program.
            System.out.print('\u000c');
            System.exit(0);
        } else 
        if(Prompt.equalsIgnoreCase("MENU"))
        {
            System.out.print('\u000c');
            StartText();
        } else 
        {
            System.out.println("Unknown command. Please try again. \n");
            MapSettings();
        }
    }

    public void ExportMap()
    {
        System.out.println("Select map style. Avalable styles: \"DEFAULT\" \"LUSH\" \"BARREN\" \"HELL\"");

        Prompt = input.nextLine(); //Detects if style input matches avalable styles.
        if(Prompt.equalsIgnoreCase("DEFAULT"))
            Style = "default";
        else if(Prompt.equalsIgnoreCase("LUSH"))
            Style = "lush";
        else if(Prompt.equalsIgnoreCase("BARREN"))
            Style = "barren";
        else if(Prompt.equalsIgnoreCase("HELL"))
            Style = "hell";
        else //If user types in anything other than avalable styles, default to "default".
        {
            System.out.println("Invalid style selected. Reverting to default style.");
            Style = "default";
        }

        System.out.println("Exporting map...");
        MapDisplay md = new MapDisplay(map, Style, width, height);

        try {TimeUnit.SECONDS.sleep(WAIT3SEC);} catch(InterruptedException e){}

        PrintMap();
        MapText();
        MapSettings();
    }

    /*
     * The following is expieremental code and not apart of the main project.
     * It is an extra feature I was playing around with.
     */

    public void GenerateMap3D()
    {
        boolean land; //Variable used to check if any land is on screen.
        double landChance = 0;

        depth = 128; //Set depth back to 128, as it will have been changed if this map has been previously generated.

        String map[][][] = new String[depth][height][width];
        //Map generation
        for (int z=0;z<depth;z++)
        {
            for (int y=0;y<height;y++)
            {
                for (int x=0;x<width;x++)
                {
                    if(z == 0) //Generate the starting map from which the following maps will be variations of.
                    {
                        if(y > 0 && y < height-1 && x > 0 && x < width-1)
                        {
                            if(Math.random() <= landChance)
                            {
                                map[z][y][x] = Land;
                                if (y > 1)
                                {
                                    if (map[z][y-1][x].equals(Land))
                                    {
                                        landChance = .98;
                                    } else
                                    {
                                        landChance = .60;
                                    }
                                } else
                                {
                                    landChance = .90;
                                }
                            } else
                            {
                                map[z][y][x] = Sea;

                                if (y > 1)
                                {
                                    if (map[z][y-1][x].equals(Sea))
                                    {
                                        landChance = .02;
                                    } else
                                    {
                                        landChance = .40;
                                    }
                                } else
                                {
                                    landChance = .10;
                                }
                            }
                        } else
                        {
                            map[z][y][x] = Coast;
                        }
                    } else
                    {
                        //Changes coastline into sea.
                        if(z > 0 && z < depth-1 && y > 0 && y < height-1 && x > 0 && x < width-1) //Checks to see if map is within bounds.
                        {
                            if(map[z-1][y][x].equals(Coast))
                            {
                                map[z-1][y][x] = Sea;
                            } else
                            {
                                map[z][y][x] = map[z-1][y][x];
                            }
                        }
                        //Changes land into coastline.
                        if(z > 0 && z < depth-1 && y > 0 && y < height-1 && x > 0 && x < width-1) //Checks to see if map is within bounds.
                        {
                            if(map[z-1][y-1][x].equals(Sea) || map[z-1][y][x-1].equals(Sea) || map[z-1][y+1][x].equals(Sea) || map[z-1][y][x+1].equals(Sea))
                            {
                                if(!map[z-1][y][x].equals(Sea))
                                {
                                    map[z][y][x] = Coast;
                                } else
                                {
                                    map[z][y][x] = map[z-1][y][x];
                                }
                            } else
                            {
                                map[z][y][x] = map[z-1][y][x];
                            }
                        } else
                        {
                            map[z][y][x] = map[z-1][y][x];
                        }
                    }
                }
            }

            land = false;

            //Checks to see if there is anx land on the board.
            for (int y=0;y<height;y++)
            {
                for (int x=0;x<width;x++)
                {
                    if(map[z][y][x].equals(Land)) 
                    {
                        land = true;
                    }
                }
            }

            if (land == false) //If no land is found on the given layer
            {
                depth = z + 1;
            }
        }

        //Map printing
        for (int a=0;a<repeats;a++)
        {
            //Print map and go through slices upwards
            for (int z=0;z<depth;z++)
            {
                System.out.print('\u000c'); //Clear pervious sice of map before generating next one.
                for (int y=0;y<height;y++)
                {
                    for (int x=0;x<width;x++)
                    {
                        System.out.print(map[z][y][x]); //Print map.
                    }
                    System.out.println(); //Return after printing line.
                }
                try {TimeUnit.MILLISECONDS.sleep(WAIT20MILLISEC);} catch(InterruptedException e){}
            }
            //Go back downwards through slices.
            for (int z=depth-1;z>-1;z--)
            {
                System.out.print('\u000c'); //Clear pervious sice of map before generating next one.
                for (int y=height-1;y>-1;y--)
                {
                    for (int x=width-1;x>-1;x--)
                    {
                        System.out.print(map[z][y][x]); //Print map.
                    }
                    System.out.println(); //Return after printing line.
                }
                try {TimeUnit.MILLISECONDS.sleep(WAIT20MILLISEC);} catch(InterruptedException e){}
            }
            try {TimeUnit.SECONDS.sleep(WAIT2SEC);} catch(InterruptedException e){}
        }

        MapText3D();
        MapSettings3D();
    }

    public void MapText3D() //3D map generation has no export feature, so MapText3D removes this option.
    {
        System.out.println("Type \"REGEN\" to generate new map.");
        System.out.println("Type \"MENU\" to return to main menu.");
        System.out.println("Type \"QUIT\" to quit.");
    }

    public void MapSettings3D()
    {
        //If statements for character input.
        Prompt = input.nextLine();
        if(Prompt.equalsIgnoreCase("REGEN"))
        {
            System.out.print('\u000c');
            GenerateMap3D();
        } else
        if(Prompt.equalsIgnoreCase("QUIT"))
        {
            //Terminates program.
            System.out.print('\u000c');

            System.exit(0);
        } else 
        if(Prompt.equalsIgnoreCase("MENU"))
        {
            System.out.print('\u000c');
            StartText();
        } else 
        {
            System.out.println("Unknown command. Please try again. \n");
            MapSettings3D();
        }
    }
}