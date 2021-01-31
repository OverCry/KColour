# KColour

Recreating a Part I project which was originally done in Matlab.

After being provided an image path, the program determines if the path is valid.
The program then randomly selects a number of points as specified by the user. 
These points determine the original set of colours.
Each pixel is calculated to see which location it is closest to. Using this information, the sample colours are refreshed to be the mean of its children.
This process is repeated until the mean colour no longer changes, or when this iterative process occures up to the specified amount.
This output is then written into a folder called "Output", found in the folder where the image originally came from. 

Added some improvements I thought would be nice.

Without the benefits of matrix multiplication, calculations were done individually for each position.

## Goals
1. Recreate the original Matlab project
2. Allow multiple colour formats, up to the desired amount, to be created.

## Compromises
1. Could not find a method of matrix multiplication. Ended up needing to slowly calculate for each pixel.


## Future improvements
1. Ensure this program works in non-Window operating systems.
2. Being able to somehow allow the user to easily provide the image path.

