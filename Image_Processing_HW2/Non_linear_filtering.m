clc
clear variables
close all

img = imread('foot.pgm');

% Non-linear filteration iterations
for i = 1:5000
    img = double(img);                  % cast to double for calculation
    [xGrad, yGrad] = gradient(img);     % image gradient and angles
    gVal = gFunction(2.,xGrad, yGrad);  % calculate the g function value with lambda values being the first calue
    xGrad = gVal .* xGrad;
    yGrad = gVal .* yGrad;
    eachDiv = divergence(xGrad, yGrad); % calculates the divergence for each iteration
    img = img + eachDiv * 0.05;         % updates the image, with del_t = 0.05
end

imwrite(uint8(img), 'non_linear_test.pgm')


% This is the g function
function toReturn  = gFunction(lambda, xGrad, yGrad)
    toReturn = 1./(1. + ((xGrad.^2. + yGrad.^2)/(lambda^2.)));
end

