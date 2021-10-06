# CSC3003S Capstone Project: VFDS

## Members: Michael Scott, Julius Smith, David Blore

# Overview

This application enables the detection of fracture voxels 
amongst CTImages. The Program displays a set of CTImage Slices
on an x, y and z axis. The user can then detect the fractures 
in the object which are grouped into different colours.

The user has the option to save the detected fracture and load
them in at a later stage. 

# Getting started 

The program should compile on all IDE's. A .Jar file is 
provided in the directory which 
can be run immediately.

# Using the GUI 

Upon running the program three windows will open showing the 
x, y and z axis. The user can select the *Load Images* button 
and then select the PGM files to load into the GUI.

Once the images have been loaded the User can use the sliders 
provided to navigate through the imageStack on the three axis.

The User Can click the *Detect Fractures* Button in order to detect
the fractures. These are displayed as groups of different colours.

The User can click the *Save Fractures* Button to save the fractures
to a specified location using the dialogue box. 

The User can click the *Load Fractures* Button to load saved fractures into a set 
of images. 

# Requirements

A working internet connection is required

# Notes

Loading in big data sets (ie 512 x 512) can take a while on slower devices. Patience is required
