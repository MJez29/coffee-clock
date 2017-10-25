from sys import *

SUCCESS = 0
BREW_ERROR = 1
COMMAND_LINE_ERROR = 2

SMALL = "Small"
MEDIUM = "Medium"
LARGE = "Large"

# Error if not enough or too many arguments passed
if len(argv) != 2 or argv[1] not in [SMALL, MEDIUM, LARGE]:
    exit(COMMAND_LINE_ERROR)

exit(SUCCESS)