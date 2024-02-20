import random
import math

# A set will be the collection of prime numbers,
# where we can select random primes p and q
prime = set()

public_key = None
private_key = None
n = None

# We will run the function only once to fill the set of
# prime numbers
def primefiller():
        # Method used to fill the primes set is Sieve of
        # Eratosthenes (a method to collect prime numbers)
        seive = [True] * 250
        seive[0] = False
        seive[1] = False
        for i in range(2, 250):
                for j in range(i * 2, 250, i):
                        seive[j] = False

        # Filling the prime numbers
        for i in range(len(seive)):
                if seive[i]:
                        prime.add(i)


# Picking a random prime number and erasing that prime
# number from list because p!=q
def pickrandomprime():
        global prime
        k = random.randint(0, len(prime) - 1)
        it = iter(prime)
        for _ in range(k):
                next(it)

        ret = next(it)
        prime.remove(ret)
        return ret


def setkeys():
        global public_key, private_key, n
        prime1 = pickrandomprime() # First prime number
        prime2 = pickrandomprime() # Second prime number

        n = prime1 * prime2
        fi = (prime1 - 1) * (prime2 - 1)

        e = 2
        while True:
                if math.gcd(e, fi) == 1:
                        break
                e += 1

        # d = (k*Φ(n) + 1) / e for some integer k
        public_key = e

        d = 2
        while True:
                if (d * e) % fi == 1:
                        break
                d += 1

        private_key = d


# To encrypt the given number
def encrypt(message):
        global public_key, n
        e = public_key
        encrypted_text = 1
        while e > 0:
                encrypted_text *= message
                encrypted_text %= n
                e -= 1
        return encrypted_text


# To decrypt the given number
def decrypt(encrypted_text):
        global private_key, n
        d = private_key
        decrypted = 1
        while d > 0:
                decrypted *= encrypted_text
                decrypted %= n
                d -= 1
        return decrypted


# First converting each character to its ASCII value and
# then encoding it then decoding the number to get the
# ASCII and converting it to character
def encoder(message):
        encoded = []
        # Calling the encrypting function in encoding function
        for letter in message:
                encoded.append(encrypt(ord(letter)))
        return encoded


def decoder(encoded):
        s = ''
        # Calling the decrypting function decoding function
        for num in encoded:
                s += chr(decrypt(num))
        return s

# Dictionary representing the morse code chart
MORSE_CODE_DICT = { 'A':'.-', 'B':'-...',
                    'C':'-.-.', 'D':'-..',
                    'E':'.','F':'..-.',
                    'G':'--.', 'H':'....',
                    'I':'..', 'J':'.---',
                    'K':'-.-','L':'.-..',
                    'M':'--', 'N':'-.',
                    'O':'---','P':'.--.',
                    'Q':'--.-','R':'.-.',
                    'S':'...', 'T':'-',
                    'U':'..-','V':'...-',
                    'W':'.--','X':'-..-',
                    'Y':'-.--', 'Z':'--..',
                    '1':'.----','2':'..---',
                    '3':'...--','4':'....-',
                    '5':'.....', '6':'-....',
                    '7':'--...', '8':'---..',
                    '9':'----.','0':'-----',
                    ', ':'--..--', '.':'.-.-.-',
                    '?':'..--..', '/':'-..-.',
                    '-':'-....-','(':'-.--.', ')':'-.--.-'}

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


def process_message_encrypt(message):
    try:
        primefiller()
        setkeys()
        coded=encoder(message)
        strmessage=' '.join(map(str,coded))
        cipher_text=encrypt_morse(strmessage.upper())

        return cipher_text
    except Exception as e:
    error_message_encrypt="Error during encryption:"+ str(e)
    return error_message_encrypt


def process_message_decrypt(cipher_text):
    try:
        result2=decrypt_morse(cipher_text)
        listresult=list(result2.split(" "))
        res1=all(isinstance(sub,type(listresult[0]))for sub in listresult[1:])
        res2=change_type(listresult)
        decoded_message=''.join(str(p) for p in decoder(res2))
        return decoded_message
    except Exception as e:
    error_message_decrypt="Error during decryption:"+str(e)
    return error_message_decrypt



#Done with the code now just to integrate with app and ca2 done


