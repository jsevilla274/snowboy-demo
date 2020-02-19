Simple Android demo application using the [Snowboy Hotword Detection](https://github.com/Kitt-AI/snowboy) library. Pressing "Start" will begin recording your voice and wait for the keyword "Snowboy" to be said. If detected, the number in the center of the screen will increment.

To test the different models provided by KITT.AI simply navigate to app/src/main/assets, replace the .umdl, and modify the variables in Globals.java with the values detailed in ["Pretrained Universal Models"](https://github.com/Kitt-AI/snowboy#pretrained-universal-models)

Tested on Windows but should work on all platforms as you do not change the structure of the project, particularly those containing the .so shared libraries.