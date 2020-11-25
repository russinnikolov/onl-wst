from functions import * 
from time import sleep 

def main():
    scr_pid = get_scr()
    ent_pid = get_ent()
    java_pid = get_java()
    ent = get_status()
    vd = get_vd()
    print('Starting main service...\n---------------------------------------')
    if scr_pid:
        print('Screens are started. '+ scr_pid )
        if ent_pid:
            print('Entangle started. ' + ent_pid + ' '+ str(ent) )
            if ent != True:
                ent_remove()
            else:
                pass
            if vd:
                print('VirtualDesktop started. ' + vd)
                if java_pid:
                    print('Java started. ' + java_pid)
                else:
                    print('Java not Working!!!')
            else:
                print('VirtualDesktop not Working!!!')
        else:
            print('Entangle not Working!!! ' + str(ent))
            if ent == True:
                run_entangle()
            else:
                ent_remove()
    else:
        pictures()
        labelPrint()
    sleep(1)
    print('\n')
while True:
     main()
