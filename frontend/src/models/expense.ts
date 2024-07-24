export default class Expense {
  constructor(
    public id: number | null,
    public denomination: string,
    public price: number,
    public accounting: number | null
  ) {}
}
