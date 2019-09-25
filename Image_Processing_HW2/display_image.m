clc
clear variables
close all
foot = imread('foot.pgm');
linear_50 = imread('Linear_filtering_50.pgm');
linear_500 = imread('Linear_filtering_500.pgm');
linear_array = cat(2, foot, linear_50, linear_500);
figure(1)
montage(linear_array)
title('Linear Filtering')
xlabel('Iterations -> 0, 50, 500')

lam2_50 = imread('Non_linear_50_10.pgm');
lam2_500 = imread('Non_linear_500_2.pgm');
lam10_50 = imread('Non_linear_50_10.pgm');
lam10_500 = imread('Non_linear_500_10.pgm');
lam2_array = cat(2, foot, lam2_50, lam2_500);
figure(2)
montage(lam2_array)
title('\lambda = 2 non-linear filtering')
xlabel('Iterations -> 0, 50, 500')

lam10_array = cat(2, foot, lam10_50, lam10_500);
figure(3)
montage(lam10_array);
title('\lambda = 10 non-linear filtering')
xlabel('Iterations -> 0, 50, 500')