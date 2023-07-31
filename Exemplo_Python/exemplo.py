# ---------------------------------------------------------------------------------------------------------------------------------------------------
# Exemplo: DLL NAPP V1.0 WIN
# 15MAI23

# ---------------------------------------------------------------------------------------------------------------------------------------------------
import ctypes
import platform

# ---------------------------------------------------------------------------------------------------------------------------------------------------
if platform.system() == "Windows":
    ffi = ctypes.WinDLL("./libE1_EGR.dll")
else:
    ffi = ctypes.cdll.LoadLibrary("./libE1_EGR.so")
    
# SetupConfigLogin ----------------------------------------------------------------------------------------------------------------------------------
def SetupConfigLogin(username, password):    
    fn = ffi.SetupConfigLogin
    fn.restype = ctypes.c_char_p
    fn.argtypes = [ctypes.c_char_p, ctypes.c_char_p] 
    
    username = ctypes.c_char_p(bytes(username, "utf-8"))
    password = ctypes.c_char_p(bytes(password, "utf-8"))
    
    return fn(username, password)

# VerificarConfigLogin ------------------------------------------------------------------------------------------------------------------------------
def VerificarConfigLogin():    
    fn = ffi.VerificarConfigLogin
    fn.restype = ctypes.c_int
    
    return fn()

# EnviarDadosVenda ----------------------------------------------------------------------------------------------------------------------------------
def EnviarDadosVenda(path):    
    fn = ffi.EnviarDadosVenda
    fn.restype = ctypes.c_char_p
    fn.argtypes = [ctypes.c_char_p] 
    
    path = ctypes.c_char_p(bytes(path, "utf-8"))
    
    return fn(path)

# EnviarDadosCatalogo -------------------------------------------------------------------------------------------------------------------------------
def EnviarDadosCatalogo(path):    
    fn = ffi.EnviarDadosCatalogo
    fn.restype = ctypes.c_char_p
    fn.argtypes = [ctypes.c_char_p] 
    
    path = ctypes.c_char_p(bytes(path, "utf-8"))
    # https://elgindevelopercommunity.github.io/group__t00.html  
    return fn(path)


# Rotina de teste -----------------------------------------------------------------------------------------------------------------------------------
# Se necessário, modifique os arquivos txt (conforme doc NAPP) para outros testes
# Atualizar o campo "ts" (time stamp - data/hora) no arquivo JSON a cada envio de dados

configOK = 0    # Arquivo de configuração já foi criado? 0 -> Não | 1 -> Sim

print("\r\n*** START ***\r\n")

print("Comando VerificarConfigLogin:", VerificarConfigLogin())

if configOK == 0:
    print("Comando SetupConfigLogin    :", SetupConfigLogin("", ""))
    print("Comando VerificarConfigLogin:", VerificarConfigLogin())

print("Comando EnviarDadosVenda    :", EnviarDadosVenda("path=./Arquivos/vendas.txt"))
print("Comando EnviarDadosCatalogo :", EnviarDadosCatalogo("path=./Arquivos/catalogo.txt"))

print("\r\n*** STOP ***\r\n")

# ---------------------------------------------------------------------------------------------------------------------------------------------------
