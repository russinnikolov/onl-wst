import re
import subprocess
import os
import shutil
import socket


def get_ip():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.connect(('10.255.255.255', 1))
        IP = s.getsockname()[0]
    except Exception:
        IP = '127.0.1.1'
    finally:
        s.close()
    return IP


#ip = socket.gethostbyname(socket.gethostname())
dirpath = '/home/mercari/Pictures/Capture/'
tmp_file = '/tmp/.X99-lock'
#device_re = re.compile(b"Bus\s+(?P<bus>\d+)\s+Device\s+(?P<device>\d+).+ID\s(?P<id>\w+:\w+)\s(?P<tag>.+)$", re.I)
#df = subprocess.check_output("lsusb")
#devices = []

def get_scr():
    scr_pid = subprocess.getoutput('pidof SCREEN')
    return scr_pid

def get_java():
    java_pid = subprocess.getoutput('pidof java')
    return java_pid

def get_vd():
    vd = subprocess.getoutput('pidof Xvfb')
    return vd

def get_ent():
    ent_pid = subprocess.getoutput('pidof entangle')
    return ent_pid

def scr_remove():
    if get_scr():
        for s in get_scr().split(' '):
            os.kill(int(s),9)
            print('Screen '+ s +' Killed.')
    else:
        print('No screens started.')

def vd_remove():
    if os.path.exists(tmp_file):
        os.remove(tmp_file)
    if get_vd():
        v = get_vd()
        os.kill(int(v),9)
    else:
        pass

def java_remove():
    if get_java():
        for j in get_java().split(' '):
            os.kill(int(j),9)
            print(''+ j + ' Java program killed.')
    else:
        print('No Java program to kill')

def pic_remove():
    shutil.rmtree(dirpath)
    os.mkdir(dirpath)
    print('Pictures folder removed and readded.')

def ent_remove():
    if get_ent():
        os.kill(int(get_ent()),9)
        print('Entangle killed')
    else:
        pass

def entangle_status():
    device_re = re.compile(b"Bus\s+(?P<bus>\d+)\s+Device\s+(?P<device>\d+).+ID\s(?P<id>\w+:\w+)\s(?P<tag>.+)$", re.I)
    df = subprocess.check_output("lsusb")
    devices = []

    for i in df.split(b'\n'):
        if i:
            info = device_re.match(i)
            if info:
                dinfo = info.groupdict()
                dinfo['device'] = '/dev/bus/usb/%s/%s' % (dinfo.pop('bus'), dinfo.pop('device'))
                devices.append(dinfo)
                for d in devices:
                    if "Canon" in str(d):
                        dev = True
                        return dev
                    else:
                        dev = False
                        pass


def get_status():
    ent = entangle_status()
    return ent

def run_vd():
    return subprocess.call('Xvfb :99 &', shell=True)

def run_entangle():
    return subprocess.call('screen -dmS Entangle bash -c "entangle --display :99"', shell=True)

def pictures():
    print('Starting Pictures script.')
    return subprocess.call('screen -dmS Pictures bash -c "java -jar /etc/razpad/pictures.jar /home/mercari/Pictures/Capture/ X"', shell=True)

def labelPrint():
    subprocess.call('screen -dmS Printer bash -c "java -jar /etc/razpad/print.jar "'+ get_ip() +'""', shell=True)
    print('Printer java script started for IP: '+ get_ip() +'!!!')


