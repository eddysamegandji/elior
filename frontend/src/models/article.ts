export default class Article {
  constructor(
    public id: number | null,
    public name: string,
    public quantity: number,
    public purchasePrice: number,
    public sellingPrice: number,
    public discount: number,
    public benefit: number,
    public date: Date | null,
    public accounting: number | null
  ) {}
}
