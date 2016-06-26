# aMEba

A simple life simulation game in Java

## Game Modes

### Survival

Objective: live as long as possible.

Controls: The organism will follow your cursor and attack when the mouse is clicked and it overlaps another organism.

The player contolled organism can move through environment, eating berries and other organisms in order to replenish their life bar, which depletes due to enemy and slowly depletes over time due to hunger. As you consume other organisms, their DNA will be added to yours, improving stats such as speed and damage.

Tips:
- Slow down and avoid others, as hunger scales up faster then the other stats, so your organism will starve if it becomes too big. Try to use berries instead.
- Other organisms will only chase you (and others) if their target is directly infront of them. If you want to avoid an enemy, sidestep or pass directly through them for minimal damage.
- Use rocks to your advantage. Other organisms will immediately face a new direction if they collide into a rock. A cluster of rocks can act as a shield against enemies.

### God Mode

Objective: sandbox mode; there is no set objective.

Controls: Interact with the buttons at the top of the screen to achieve various effects. Click on the screen to spawn things. Scroll by moving the mouse cursor to the edge of the screen.

You are given free rein over an environment of organisms. Change the rate of which berries and enemies spawn, spawn individual enemies and berries, change the landscape with rocks to block organisms, and destroy everything as you please.


## Dependancies

[JLayer 1.0.1](http://www.javazoom.net/javalayer/javalayer.html) (for audio)