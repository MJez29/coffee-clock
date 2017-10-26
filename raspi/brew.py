from sys import *
from time import *

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

# Pause for 5s
sleep(20)

exit(SUCCESS)