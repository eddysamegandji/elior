import type Product from './product'

export default class Article {
  constructor(
    public id: number | null,
    public quantity: number,
    public totalArticlePurchasePrice: number,
    public sellingPrice: number,
    public discount: number,
    public benefit: number,
    public date: Date | null,
    public product: Product,
    public accounting: number | null
  ) {}
}
