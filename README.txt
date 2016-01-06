README

System Requirements:
Java 1.8 update 66
Tested on Windows 10

Installation:
Extract the zip file


Execution:
Run the jar file
The chosen zip files for training must have its hierarchy as follows:

-ROOT
--Class 1 -- File 1.txt
	  -- File 2.txt
--Class 2 -- File 3.txt
	  -- File 4.txt
	  -- File 5.txt
--Class 3 -- File 6.txt

The names of the maps are the chosen class names after training.

Interactions:
The GUI starts off with a screen with three options, train, classify and test. The train button is the only one available in the beginning.

Train:
A screen is shown in which the user can upload a zipfile to train.
After the screen is gone, the user will be back to the original screen and the classify and test options are available now.

Classify:
The user can choose between classifying a single txt file or a zipfile with multiple txt files.
When classifying one text file, a screen is shown in which the user can see the outcome of the classification process and is able to give feedback as to which class it was.
After the feedback, the classifier will train again with the newly acquired document.
With multiple files, there are multiple screens, so beware of using this with a lot of text files in the zip.

Test:
When testing, the user can upload a zip file to test the classifier on and a location as to where the user wants to retrieve the result.txt in which the output is shown.