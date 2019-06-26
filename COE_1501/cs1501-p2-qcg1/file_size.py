#!/usr/bin/env python
import os
import sys

if (len(sys.argv) == 2):
    print(os.path.getsize(sys.argv[1]))
else:
    print(os.path.getsize(sys.argv[1]) / float(os.path.getsize(sys.argv[2])))
