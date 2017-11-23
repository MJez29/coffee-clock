from sys import *
import time
import RPi.GPIO as GPIO

# The script that brews the coffee

GPIO.setmode(GPIO.BOARD)

# The pin that controls the servo that simulates opening and closing the lid
LID_SERVO_PIN = 11

# The pin that controls the servo that presses the coffee size button
BREW_SIZE_SERVO_PIN = 15

GPIO.setup(LID_SERVO_PIN, GPIO.OUT)
GPIO.setup(BREW_SIZE_SERVO_PIN, GPIO.OUT)

lidServo = GPIO.PWM(11, 50)
brewServo = GPIO.PWM(15, 50)

print("Moving servos to correct positions")

# Makes sure the servos are in the correct start position

lidServo.start(7.5)
brewServo.start(2.5)

time.sleep(2)

# Simulates closing the lid

lidServo.ChangeDutyCycle(2.5)

print("Lid closed sensor pressed")

time.sleep(2)

# Presses the medium coffee button

brewServo.ChangeDutyCycle(7.5)

time.sleep(0.5)

brewServo.ChangeDutyCycle(2.5)

print("Brew button pressed")

# Waits for the coffee to brew and gives enough time to add more water to the Keurig and to replace the K-Cup

time.sleep(120)

# Returns to original positions

lidServo.ChangeDutyCycle(7.5)
brewServo.ChangeDutyCycle(2.5)

print("Servos back to original position")

time.sleep(1)

# Cleans up the pins when finished

brewServo.stop()
lidServo.stop()
GPIO.cleanup()
