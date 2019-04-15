import sys
import networkx as nx
import random as random
import csv

#insert amount of vertexes and edges here
vertexes =
edges =

#generates random graph with vertexes and edges given
G = nx.dense_gnm_random_graph(vertexes,edges,None)

#prints density based on a directed graph density equation
dens = (edges/(vertexes*(vertexes-1)))

print('density is ' + str(dens))

#provides a random weight to each edge
for (u, v) in G.edges():
    randWeight = random.randint(1,100)
    G[u][v]['weight'] = randWeight

#change filename here
filename = 'example.csv'

#writes edgelists to a csv file
with open(filename, "w") as csv_file:
    writer = csv.writer(csv_file)

    for line in nx.generate_edgelist(G, data=['weight']):
        writer.writerow(line.split())
