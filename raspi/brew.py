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

print("About to start")

lidServo.start(7.5)
brewServo.start(12.5)

print("Servos started (sleep for 2s)")

time.sleep(2)

lidServo.ChangeDutyCycle(2.5)

print("Lid servo duty cycle changed (sleep 1s)")

time.sleep(1)

brewServo.ChangeDutyCycle(7.5)

print("Brew servo duty cycle changed (sleep 1s)")

time.sleep(1)

brewServo.ChangeDutyCycle(12.5)

print("Brew servo duty cycle changed (all done)")

time.sleep(1)

# Returns to original positions

lidServo.start(7.5)
brewServo.start(12.5)

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
