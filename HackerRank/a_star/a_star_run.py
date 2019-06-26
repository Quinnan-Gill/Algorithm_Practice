from graphics import *
from point_set import *

# each box is going to be 15 pixels in h and w
width = 525 # 15 for width and there are 35 boxes
box_height = 375 # 15 per height and there are 25 boxes

box_size = 15
num_boxes_w = 35
num_boxes_h = 25

height = box_height + 50

def main():
    win = GraphWin("A-Star", width, height, autoflush=False)

    msg = create_grid(win)
    print("Created Grid")

    start = start_position(win, msg)

    end = end_postion(win, msg)

    obs = get_obsticle(win, msg)

    print_obs(obs)

    print("DONE")
    win.getMouse() # pause for click in window

    win.close()

def create_grid(win):
    msg = Text(Point(width/2, box_height + 25), "Choose a starting position")
    msg.setSize(25)
    msg.draw(win)
    for i in range(0, num_boxes_w):
        for j in range(0, num_boxes_h):
            sq = Rectangle(Point(i*box_size, j*box_size),
                Point(i*box_size + 15, j*box_size + 15))
            sq.setFill(color_rgb(224, 224, 214))
            sq.draw(win)

    return msg

def start_position(win, msg):
    p = win.getMouse()
    x_p = p.getX()
    y_p = p.getY()

    print("Start point at: (" + str(x_p) + ", " + str(y_p) + ")")

    start_x = x_p - (x_p % box_size)
    start_y = y_p - (y_p % box_size)

    start = Rectangle(Point(start_x, start_y),
        Point(start_x+15, start_y+15))
    start.setFill(color_rgb(238,133,132))

    start.draw(win)

    msg.setText("Pick End Point")

    return start

def end_postion(win, msg):
    p = win.getMouse()
    x_p = p.getX()
    y_p = p.getY()

    print("End point at: (" + str(x_p) + ", " + str(y_p) + ")")

    end_x = x_p - (x_p % box_size)
    end_y = y_p - (y_p % box_size)

    end = Rectangle(Point(end_x, end_y),
        Point(end_x+15, end_y+15))
    end.setFill(color_rgb(126,132,247))

    end.draw(win)

    return end

def get_obsticle(win, msg):
    msg, bounds = set_obs_msg(msg, win)

    print(msg.getText())

    obs = PointSet()
    pos = win.getMouse()
    print(check_done(pos, bounds))
    while not check_done(pos, bounds):
        # print "The check is:", check_done(pos, bounds)
        ob = set_obs(win, pos)

        if ob != None:
            obs.add(ob)
            # print "LENGTH OF SET:", obs.get_length()

        pos = win.getMouse()


    return obs

def set_obs_msg(msg, win):
    msg.undraw()
    msg = Text(Point(width/3, box_height + 25), "Set Boundary")
    msg.setSize(18)
    msg.draw(win)

    b_box_x = 2*width/3
    b_box_y = box_height + 15

    db_x = 30
    db_y = 20

    button_box = Rectangle(Point(b_box_x, b_box_y),
        Point(b_box_x+db_x, b_box_y+db_y))
    button_box.draw(win)

    done = Text(Point(b_box_x+15, b_box_y+10), "Done")
    done.draw(win)

    return msg, [b_box_x, b_box_y, b_box_x+db_x, b_box_y+db_y]

def check_done(pos, bounds):
    pos_x = pos.getX()
    pos_y = pos.getY()

    # print pos_x, ">=", bounds[0]
    # print pos_x, "<=", bounds[2]
    # print pos_y, ">=", bounds[1]
    # print pos_y, "<=", bounds[3]
    return (pos_x >= bounds[0] and pos_x <= bounds[2]) \
        and (pos_y >= bounds[1] and pos_y <= bounds[3])

def set_obs(win, pos):
    pos_x = pos.getX()
    pos_y = pos.getY()
    if not(pos_x > width or pos_y > box_height):
        obs_x = pos_x - (pos_x % 15)
        obs_y = pos_y - (pos_y % 15)
        obs = Rectangle(Point(obs_x, obs_y),
            Point(obs_x+box_size, obs_y+box_size))
        obs.setFill(color_rgb(0,0,0))

        obs.draw(win)

        return Point(obs_x, obs_y)
    else:
        return None

def print_obs(obs):
    for ob in obs.get_points_as_list():
        print("(" + str(ob.x) + ", " + str(ob.y), end="")

if __name__ == '__main__':
    main()
