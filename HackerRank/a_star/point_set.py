from graphics import *

class PointSet(object):
    def __init__(self):
        self.point_set = set()

    def add(self, p):
        self.point_set.add((p.getX(), p.getY()))

    def get_points_as_list(self):
        points = []
        for i in range(len(self.point_set)):
            x = list(self.point_set)[i]
            y = list(self.point_set)[i]
            points.append(Point(x,y))
        return points

    def get_point(self, i):
        x = list(self.point_set)[i]
        y = list(self.point_set)[i]
        return Point(x, y)

    def get_length(self):
        return len(self.point_set)

    def find_point(p):
        p_x = p.getX()
        p_y = p.getY()

        for i in range(self.length):
            x = list(self.point_set)[i]
            y = list(self.point_set)[i]
            if p_x == x and p_y == y:
                return i
        return -1
