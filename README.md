# Filter API

Este projeto apresenta uma API simples e eficiente para filtragem de dados. Projetado para facilitar a manipulação e o acesso a conjuntos de dados extensos, a API oferece uma variedade de opções de filtragem, ordenação e paginação para atender às necessidades de diferentes cenários de uso.

Todas as entidades abaixo, possuem filtragem e todas as filtragens podem ser combinadas.

### Paginação

O limit máximo por página é 100

```bash
   http://localhost:8080/[controller]?page=1&limit=10
```

### Ordenação

**-* - Indica que  a propriedade ser ordenada é DESC, na sua ausência é ASC

```bash
    http://localhost:8080/[controller]?sort=-name;id
```


### Filtragem por Datas

```bash
    http://localhost:8080/[controller]/date_filters=attribute:2000-04-10to2000-04-10;attribute:2000-04-10to2000-04-10;
```


### Filtragem por Igualdade


**!=** - Indica que o atributo tem que ser diferente do valor especificado.

```bash
   http://localhost:8080/[controller]?equal_filters=id:=10;name:!=Gabriel
```

Exemplo


### Filtragem por In

**~** - Indica que é uma negação e que é pra filtrar tudo que não tiver dentro do range.

```bash
   http://localhost:8080/[controller]?in_filters=id:1,2,3,4;~name:José,Carlos,Maria
```

### Entidades

#### Product

#### Category

#### Supplier

#### Feito por amor por mim <3
