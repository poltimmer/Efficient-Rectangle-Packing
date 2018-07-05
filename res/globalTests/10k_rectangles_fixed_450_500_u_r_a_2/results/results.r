data <-read.table("C:/Users/s169014/Documents/School/[2IO90] DBL Algorithms/Project/2IO90-RectanglePacking-24/res/averageCalculator/output/results.csv",sep=";",skip="2")
readingRuntime <- data$V1
solverRuntime <- data$V2
totalRuntime <- data$V3
n <- data$V4
containerWidth <- data$V5
containerHeight <- data$V6
containerArea <- data$V7
coverage <- data$V8

mean(totalRuntime)/1000000000
