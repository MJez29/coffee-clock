from sys import *
import time
import RPi.GPIO as GPIO
'''
SUCCESS = 0
BREW_ERROR = 2
COMMAND_LINE_ERROR = 3

SMALL = "Small"
MEDIUM = "Medium"
LARGE = "Large"

# Error if not enough or too many arguments passed
if len(argv) != 2 or argv[1] not in [SMALL, MEDIUM, LARGE]:
    print(argv)
    exit(COMMAND_LINE_ERROR)
'''

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

lidServo.start(7.5)
brewServo.start(2.5)

time.sleep(2)

lidServo.ChangeDutyCycle(2.5)

print("Lid closed sensor pressed")

time.sleep(2)

brewServo.ChangeDutyCycle(7.5)

time.sleep(0.5)

brewServo.ChangeDutyCycle(2.5)

print("Brew button pressed")

time.sleep(90)

# Returns to original positions

lidServo.ChangeDutyCycle(7.5)
brewServo.ChangeDutyCycle(2.5)

print("Servos back to original position")

time.sleep(1)

brewServo.stop()
lidServo.stop()
GPIO.cleanup()

'''

try:
    while True:
        print("Ed:D")
        p.ChangeDutyCycle(7.5)
        time.sleep(1)
        q.ChangeDutyCycle(12.5)
        time.sleep(1)
        p.ChangeDutyCycle(2.5)
        time.sleep(1)
        q.ChangeDutyCycle(2.5)
        time.sleep(1)
        p.ChangeDutyCycle(12.5)
        time.sleep(1)
        q.ChangeDutyCycle(7.5)
        time.sleep(1)
except:
    p.stop()
    q.stop()
    GPIO.cleanup()
'''
