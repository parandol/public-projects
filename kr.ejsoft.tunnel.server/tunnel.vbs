Set objShell = CreateObject("Shell.Application")
objShell.ShellExecute "........\tunnel.bat", "/c lodctr.exe /r" , "", "runas", 0
