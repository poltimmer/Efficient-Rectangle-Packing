# Efficient Rectangle Packing
This repo offers several solutions to the 2d rectangle packing problem. This is a project for an undergrad TU/e course "2IO90 DBL Algorithms".

In a packing problem we are given a collection of objects that we wish to pack into some kind of container. An application of such a problem is when we want to place a set of boxes into a moving van, or a set of suitcases into a car trunk. There are also applications that give rise to 2-dimensional packing problems. For example, suppose we have a large piece of leather from which we want to cut out certain shapes to make leather bags. Then finding a good layout for the shapes on the given piece of leather can be viewed as a packing problem.

In this DBL project we consider two variants of a 2-dimensional packing problem where the goal is to place a set S of n rectangles of various sizes (with edges parallel to the x- and y-axis) into a minimum-area rectangular container (with edges parallel to the x- and y-axis). In such a placement, the rectangles should not overlap with each other, that is, they should have disjoint interiors. The difference between the two variants is the type of containers we can use.
* In the first variant we are allowed any type of rectangle as container. The problem can be then stated as follows: place the rectangles such that their bounding box has minimum area. (The bounding box of a set of objects is the smallest axis-aligned rectangle containing all the objects.)
* In the second variant the height of the container, H, is specified. In other words, we want to place the rectangles into a rectangle of height H whose width is minimized. For both variants, we will consider two versions: one where we are allowed to rotate the input rectangles by 90 degrees, and one where rotations are not allowed.

This Java software repository aims to solve the above problems.
