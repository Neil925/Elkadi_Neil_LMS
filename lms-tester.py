import subprocess

def main():
    file = open("test_input.txt", "w")
    file.write("l\nr\n1\na\nl\nr\n1004\nl\nq\ny\n")
    file.close()

    file = open("import_file.txt", "w")
    file.write("# EXAMPLE:\n# 1505, My Book, Neil Elkadi\n")

    file.write("\n1001, The Great Gatsby, F. Scott Fitzgerald")
    file.write("\n1002, 1984, George Orwell")
    file.write("\n1003, To Kill a Mockingbird, Harper Lee")
    file.write("\n1004, Moby-Dick, Herman Melville")
    file.write("\n1005, Pride and Prejudice, Jane Austen")
    file.write("\n1006, The Catcher in the Rye, J.D. Salinger")
    file.write("\n1007, War and Peace, Leo Tolstoy")
    file.write("\n1008, The Hobbit, J.R.R. Tolkien")
    file.write("\n1009, Crime and Punishment, Fyodor Dostoevsky")
    file.write("\n1010, Ulysses, James Joyce")

    file = open("test_input.txt", "r")
    subprocess.run(["java", "./src/lms/Main.java"], stdin=file)

if __name__ == "__main__":
    main()
