/**
 * @author Baxter J B Berdinner - Inspired by Bill Viggers
 * @version 15/06/2022
 */

//Import nessesary java extensions.
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MapDisplay extends JFrame
{
    final int IMGSIZE = 24; //The size in pixels of all images.

    final int SCREENWIDTH;
    final int SCREENHEIGHT; //Variables that will control the size of the window.

    final int YOFFSET = 8;
    final int XOFFSET = 32;

    public String Land = ("██");
    public String Hills = ("▓▓");
    public String Coast = ("▒▒");
    public String Sea = ("░░");

    //Declaration of image variables
    ImageIcon landImage;
    ImageIcon hillsImage;
    ImageIcon seaImage;
    ImageIcon forestImage;

    ImageIcon coastImage;
    ImageIcon coastMultiImage;
    ImageIcon coastUpImage;
    ImageIcon coastDownImage;
    ImageIcon coastLeftImage;
    ImageIcon coastRightImage;

    ImageIcon borderImage;

    String displayMap[][];
    String Style = new String();
    final int WIDTH;
    final int HEIGHT;

    //Passes through relevant variables from class main and declares nessesary variables and sets up jFrame.
    public MapDisplay(String[][] m, String style, int width, int height) //Passing variables from Main class into MapDisplay.
    {
        //Setting MapDisplay variables to variables from Main.
        displayMap = m;
        Style = style;
        WIDTH = width;
        HEIGHT = height;

        SCREENWIDTH = IMGSIZE*WIDTH;
        SCREENHEIGHT = IMGSIZE*HEIGHT;

        //Image variables are set after style has been set.
        landImage = new ImageIcon("images/"+Style+"/land.png");
        hillsImage = new ImageIcon("images/"+Style+"/hills.png");
        seaImage = new ImageIcon("images/"+Style+"/sea.png");
        forestImage = new ImageIcon("images/"+Style+"/forest.png");

        coastImage = new ImageIcon("images/"+Style+"/coast.png");
        coastMultiImage = new ImageIcon("images/"+Style+"/coastmulti.png");
        coastUpImage = new ImageIcon("images/"+Style+"/coastup.png");
        coastDownImage = new ImageIcon("images/"+Style+"/coastdown.png");
        coastLeftImage = new ImageIcon("images/"+Style+"/coastleft.png");
        coastRightImage = new ImageIcon("images/"+Style+"/coastright.png");

        borderImage = new ImageIcon("images/"+Style+"/border.png");

        setTitle("Map Generator 9000 - Export");

        this.getContentPane().setPreferredSize(new Dimension(SCREENWIDTH,SCREENHEIGHT));  
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();
        this.setVisible(true);
    }

    public void paint(Graphics g) //Function takes map 2d array and prints out on screen representing each cell as an image.
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int x=0;x<HEIGHT;x++)
        {
            for (int y=0;y<WIDTH;y++)
            {
                if(x > 0 && x < HEIGHT-1 && y > 0 && y < WIDTH-1)
                {
                    if(displayMap[x][y].equals(Land)) //Checks to see if specified image is of type Land.
                        if(!displayMap[x][y-1].equals(Land) || !displayMap[x-1][y].equals(Land) || !displayMap[x][y+1].equals(Land) || !displayMap[x+1][y].equals(Land))
                        {
                            if(Math.random() > 0.8)
                            {
                                forestImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                            } else
                                landImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET); //Paints land image on screen.
                        } else
                            forestImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                    if(displayMap[x][y].equals(Hills)) //Checks to see if specified image is of type Hills.
                        hillsImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET); //Paints hills image on screen.
                    if(displayMap[x][y].equals(Sea)) //Checks to see if specified image is of type Sea.
                        seaImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET); //Paints sea image on screen.

                    int adjacentLand = 0; //Resets adjacentland int to zero each time.
                    if(displayMap[x][y].equals(Coast)) //Checks to see if specified image is of type Coast.
                    {
                        //Checks each surrounding cell of coast for land or hills, and counts how many calls of that type are present.
                        //If only one surroundig cell is of type land or hills, the coast is set to the variation that faces in said direction.
                        if(displayMap[x][y-1].equals(Land) || displayMap[x][y-1].equals(Hills))
                        {
                            coastLeftImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                            adjacentLand++;
                        }
                        if(displayMap[x][y+1].equals(Land) || displayMap[x][y+1].equals(Hills))
                        {
                            coastRightImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                            adjacentLand++;
                        }
                        if(displayMap[x-1][y].equals(Land) || displayMap[x-1][y].equals(Hills))
                        {
                            coastUpImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                            adjacentLand++;
                        }
                        if(displayMap[x+1][y].equals(Land) || displayMap[x+1][y].equals(Hills))
                        {
                            coastDownImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                            adjacentLand++;
                        }
                        //If multiple or zero pieces of land are present surrounding a coast, set coast variation to milti or default.
                        if(adjacentLand >= 2)
                            coastMultiImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                        if(adjacentLand == 0)
                            coastImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET);
                    }
                } else
                    borderImage.paintIcon(this, g, y*IMGSIZE+YOFFSET,x*IMGSIZE+XOFFSET); //Set border image around the outsides of the map.
            }
        }
    }
}