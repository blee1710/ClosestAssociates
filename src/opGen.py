#!/usr/bin/python

import csv
import random
import sys

class OpGen():
    def __init__(self, graphFile):
        random.seed()

        self.edges = []

        #Load csv file
        with open(graphFile, 'rb') as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                self.edges.append(row)

        self.edgeCount = len(self.edges)

    def genOps(self, format, count):
        for i in range(count):
            print(self.genOp(format))

    def genOp(self, format):
        resolvedTokens = []
        tokens = format.split(' ')

        for token in tokens:
            if token.startswith('%'):
                resolvedTokens.append(self.resolve(token))
            else:
                resolvedTokens.append(token)

        return ' '.join(resolvedTokens)

    def resolve(self, token):
        command = token[1:].lower()

        if command == "ev":
            edgeIndex = random.randint(0, self.edgeCount - 1)
            randomEdge = self.edges[edgeIndex]
            randomSide = random.randint(0, 1)
            return randomEdge[randomSide]

        elif command == "ze":
            edgeIndex = random.randint(0, self.edgeCount - 1)
            randomEdge = self.edges[edgeIndex]
            randomEdge[2] = "0"
            return str(' '.join(randomEdge))
        elif command == "ue":
            edgeIndex = random.randint(0, self.edgeCount - 1)
            randomEdge = self.edges[edgeIndex]
            #Update weight to random weight
            randomEdge[2] = str(random.randint(1, 100))
            
            return str(' '.join(randomEdge))

if len(sys.argv) != 4:
    print("""Usage: opGen.py <graphfile> <format> <no. operations>

Where format can include the following special tokens
%ev : use an existing vertex from the graph
%ze : use an existing edge from the graph with a weight of zero
%ue : use an edge from the graph with an updated weight

Example: 'rv %ev' will generate commands such as
'rv 42'
'rv 102"
'rv 921'""")

else:
    opGen = OpGen(sys.argv[1])
    opGen.genOps(sys.argv[2], int(sys.argv[3]))
