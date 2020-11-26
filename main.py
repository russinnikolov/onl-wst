from functions import * 
from time import sleep 
def main():
    scr_pid = get_scr()
    ent_pid = get_ent()
    java_pid = get_java()
    ent = get_status()
    #vd = get_vd()
    print('Starting main service...\n---------------------------------------')
    if scr_pid:
        print('Screens are started. '+ scr_pid )
        if java_pid:
            print('Java programs started. ' + java_pid)
            if get_vd():
                print('VirtualDesktop started. ' + get_vd())
                if ent != True:
                    vd_remove()
                else:
                    pass
                if ent_pid:
                    print('Entangle PID Exists. ' + ent_pid)
                    if ent != True:
                        vd_remove()
                        ent_remove()
                    else:
                        pass                                                
                else:
                    print('Entangle PID does not exist.' + str(ent))
                    if ent == True:
                        run_vd()
                        run_entangle()
                    else:
                        pass
            else:
                print('VirtualDesktop not Working!!!')
                run_vd()
        else:
            print('Java not Working!!! ')
            if ent == True:
                run_entangle()
            else:
                pass
    else:
        pictures()
        labelPrint()
    sleep(1)
    print('\n')

while True:

     main()
