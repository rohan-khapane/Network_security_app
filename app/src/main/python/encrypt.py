import random
import math

public_key = None
private_key = None
n = None

# To encrypt the given number
def encrypt(message,public_key,n):
    #global public_key, n
    c=pow(message,public_key,n)
    #print(c)
    return c

# To decrypt the given number
def decrypt(encrypted_text,private_key,n):
    #global private_key, n
    p=pow(encrypted_text,private_key,n)
    #print(p)
    return p


# First converting each character to its ASCII value and
# then encoding it then decoding the number to get the
# ASCII and converting it to character
def encoder(message,public_key,n):
    encoded = []
    # Calling the encrypting function in encoding function
    for letter in message:
            encoded.append(encrypt(ord(letter),public_key,n))
    return encoded

def decoder(encoded,private_key,n):
    s = ''
    # Calling the decrypting function decoding function
    for num in encoded:
            s += chr(decrypt(num,private_key,n))
    return s


# Dictionary representing the morse code chart
MORSE_CODE_DICT = { '1':'.----','2':'..---',
                    '3':'...--','4':'....-',
                    '5':'.....', '6':'-....',
                    '7':'--...', '8':'---..',
                    '9':'----.','0':'-----'}


# Function to encrypt the string
# according to the morse code chart
def encrypt_morse(message):
    cipher = ''
    for letter in message:
        if letter != ' ':
            # Looks up the dictionary and adds the
            # corresponding morse code
            # along with a space to separate
            # morse codes for different characters
            cipher += MORSE_CODE_DICT[letter] + ' '
        else:
            # 1 space indicates different characters
            # and 2 indicates different words
            cipher += ' '

    return cipher




# Function to decrypt the string
# from morse to english
def decrypt_morse(message):

    # extra space added at the end to access the
    # last morse code
    message += ' '

    decipher = ''
    citext = ''
    for letter in message:

        # checks for space
        if (letter != ' '):

            # counter to keep track of space
            i = 0
            #print(i,"\n")

            # storing morse code of a single character
            citext += letter
            #print(citext,"decrypt if")

        # in case of space
        else:
            # if i = 1 that indicates a new character
            i += 1
            #print(i,"\n")

            # if i = 2 that indicates a new word
            if i == 2 :

                # adding space to separate words
                decipher += ' '
                #print(i,"\n")
                #print(decipher,"Decipher")
            else:
                # accessing the keys using their values (reverse of encryption)
                decipher += list(MORSE_CODE_DICT.keys())[list(MORSE_CODE_DICT.values()).index(citext)]
                #print(list(MORSE_CODE_DICT.keys()),"\n")
                #print([list(MORSE_CODE_DICT
                #.values())])

                #print("\n",[list(MORSE_CODE_DICT.values()).index(citext)])
                citext = ''
                #print(decipher,"\n")
                #print(citext,"decrpyt else")

    return decipher

def change_type(sub):
    if isinstance(sub, list):
        return [change_type(ele) for ele in sub if ele.strip()]
    elif isinstance(sub, tuple):
        return tuple(change_type(ele) for ele in sub if str(ele).strip())
    else:
        return int(sub)



def process_message_encrypt(message,public_key,n):
    try:
        coded=encoder(message,public_key,n)
        strmessage=' '.join(map(str,coded))
        #print(strmessage)
        cipher_text=encrypt_morse(strmessage.upper())

        return cipher_text

    except Exception as e:
        error_message_encrypt="Error during encryption:"+ str(e)
        return error_message_encrypt


def process_message_decrypt(cipher_text,private_key,n):
    try:
        result2=decrypt_morse(cipher_text)
        listresult=list(result2.split(" "))
        res1=all(isinstance(sub,type(listresult[0]))for sub in listresult[1:])
        res2=change_type(listresult)
        decoded_message=''.join(str(p) for p in decoder(res2,private_key,n))
        return decoded_message
    except Exception as e:
        error_message_decrypt="Error during decryption:"+str(e)
        raise error_message_decrypt

#m="hello @world! !!"
#r=process_message_encrypt(m,3,4399)
#print(r)
#d=process_message_decrypt(r,2843,4399)
#print(d)
