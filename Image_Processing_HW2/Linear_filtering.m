clc
clear variables
close all

img = imread('foot.pgm');
for i = 1:500
    img = double(img);                  % cast to double for calculation
    eachDiv = del2(img); 
    img = img + eachDiv * 0.05;         % updates the image, with del_t = 0.05
end

imwrite(uint8(img), 'linear_test.pgm')